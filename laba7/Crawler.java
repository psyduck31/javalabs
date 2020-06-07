import java.io.BufferedReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.UnknownHostException;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.net.SocketTimeoutException;

public class Crawler 
{


	static final String CONST1 = "(href=\").*?\"";

	static final String CONST2 = "(<a).*?>";

	private static final Pattern TAG_A_PATTEN_COMPILE = Pattern.compile(CONST2);

	private static final Pattern CONST1_COMPILE = Pattern.compile(CONST1);

	private HashSet<String> allUrls = new HashSet<String>();

	private HashSet<String> allDomain = new HashSet<String>();

	private LinkedBlockingDeque <URLDepthPair> unCheckedList = new LinkedBlockingDeque <URLDepthPair>();

	private LinkedList <URLDepthPair> checkedList = new LinkedList <URLDepthPair>();

	private LinkedList <URLDepthPair> noneCheckedList = new LinkedList <URLDepthPair>();

	private int maxDepth;

	public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, SQLException, InterruptedException 
	{
		if (args.length != 2) {
			System.out.println("Bad format\nYou should start app like this:\njava Crawler <URL> <MAX_DEPTH>");
			return;
		}
		String url = args[0];
		Integer depth = Integer.parseInt(args[1]);
		Crawler crawl = new Crawler(url, depth);
		crawl.startCrawl();
 	}


	public Crawler(String url, int maxDepth) throws UnknownHostException, IOException, ClassNotFoundException, SQLException
	{
		URLDepthPair urlpair = new URLDepthPair(url,0);
		unCheckedList.add(urlpair);
		allUrls.add(urlpair.url);
		allDomain.add(urlpair.domain);
		this.maxDepth = maxDepth;
	}



	public void startCrawl() throws IOException, InterruptedException
	{
		while(!unCheckedList.isEmpty())
		{
			URLDepthPair urlpair = unCheckedList.pollFirst();
			if (urlpair.depth <= maxDepth) 
			{
				if(readURL(urlpair)) checkedList.addLast(urlpair);
			}
			else noneCheckedList.add(urlpair);
		}
	}

	private boolean readURL(URLDepthPair urlpair) throws IOException, SocketTimeoutException
	{
		try {
			SocketConnection conn = new SocketConnection(urlpair);
			if (!conn.connect()) return false;
			conn.sendGET();
			if (Integer.parseInt(conn.code) == 200)
			{
				System.out.printf("[%s]: good request, status code: %s\n", urlpair.url, conn.code);
				String line;
				BufferedReader br = conn.br;
				while ((line = br.readLine()) != null) 
				{
					ParseNewURL(line,urlpair);
				}
				conn.close();
				return true;
				
			}
			else {
				System.out.printf("[%s]: bad request, status code: %s\n", urlpair.url, conn.code);
				return false;
			}
		}
		catch (SocketTimeoutException exception) {
			System.out.printf("[%s]: connection timeout\n", urlpair.url);
			return false;
		}
	}

	private boolean ParseNewURL(String line, URLDepthPair url)
	{
		try
		{
			Matcher m = TAG_A_PATTEN_COMPILE.matcher(line);
			m.find();
			String tagA = line.substring(m.start(),m.end());
			Matcher m2 = CONST1_COMPILE.matcher(tagA);
			m2.find();
			String href = tagA.substring(m2.start(),m2.end());
			URLDepthPair newURL = new URLDepthPair(href,url.depth+1,url.domain);

			if (!allUrls.contains(newURL.url))
			{
				allUrls.add(newURL.url);
				if (allDomain.contains(newURL.domain)) unCheckedList.addLast(newURL);
				else 
				{
					unCheckedList.addFirst(newURL);
					allDomain.add(newURL.domain);
				}
					
				return true;
			}
			else return false;
			
		}
		catch (IllegalStateException e)
		{
			return false;
		} 
		catch (MalformedURLException e) 
		{
			return false;
		}
	}

	public LinkedList <URLDepthPair> getCheckedSites()
	{
		return checkedList;
	}

	public LinkedList <URLDepthPair> getUncheckedSites()
	{
		return noneCheckedList;
	}

}

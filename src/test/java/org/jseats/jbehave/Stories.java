package org.jseats.jbehave;


import static org.jbehave.core.io.CodeLocations.codeLocationFromClass;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import org.jbehave.core.configuration.Configuration;
import org.jbehave.core.configuration.MostUsefulConfiguration;
import org.jbehave.core.io.LoadFromClasspath;
import org.jbehave.core.io.StoryFinder;
import org.jbehave.core.junit.JUnitStories;
import org.jbehave.core.reporters.Format;
import org.jbehave.core.reporters.StoryReporterBuilder;
import org.jbehave.core.steps.InjectableStepsFactory;
import org.jbehave.core.steps.InstanceStepsFactory;
import org.junit.runner.RunWith;

import de.codecentric.jbehave.junit.monitoring.JUnitReportingRunner;

@RunWith (JUnitReportingRunner.class)
public class Stories extends JUnitStories {

	public Stories () {
		configuredEmbedder ().embedderControls ()
		.doGenerateViewAfterStories (true)
		.doIgnoreFailureInStories (false).doIgnoreFailureInView (false)
		.useStoryTimeoutInSecs (600);
	}

	@Override
	public Configuration configuration () {
		return new MostUsefulConfiguration ()
		       // where to find the stories
		       .useStoryLoader (new LoadFromClasspath (this.getClass ()))
		       // CONSOLE and TXT reporting
		       .useStoryReporterBuilder (
		           new StoryReporterBuilder ().withDefaultFormats ()
		           .withFailureTrace (true)
		           .withFormats (Format.CONSOLE, Format.HTML));
	}

	//TODO AGB remove duplication with com.scytl.rcp.consolidation.impl.internal.AbstractTestConsolidationImpl#getResourceListing(java.lang.Class<?>, java.lang.String)
	private String[] getResourceListing (final Class<?> clazz,
	                                     final String path) throws URISyntaxException, IOException {
		URL dirURL = clazz.getClassLoader ().getResource (path);

		if (dirURL != null && dirURL.getProtocol ().equals ("file")) {
			return new File (dirURL.toURI ()).list ();
		}

		if (dirURL == null) {
			String me = clazz.getName ().replace (".", "/") + ".class";
			dirURL = clazz.getClassLoader ().getResource (me);
		}

		String location =
		    dirURL.toString ()
		    .replace (this.getClass ().getName ().replace (".", "/"), "")
		    .replace (".class", "").replace ("target/test-classes/", "")
		    + "src/test/resources" + path;
		File f = new File (location.replace ("file:/", "/"));

		if (f.exists () && f.isDirectory ()) {
			String[] list = f.list ();

			//TODO AGB pointer to return values => f.listFiles()
			for (int i = 0; i < list.length; i++) {
				list[i] = path + "/" + list[i];
			}

			return list;
		}

		if (dirURL.getProtocol ().equals ("jar")) {
			String jarPath =
			    dirURL.getPath ().substring (5,
			                                 dirURL.getPath ().indexOf ("!"));

			JarFile jar = null;
			Set<String> result = new HashSet<String> ();

			try {
				jar = new JarFile (URLDecoder.decode (jarPath, "UTF-8"));
				Enumeration<JarEntry> entries = jar.entries ();

				while (entries.hasMoreElements ()) {
					String name = entries.nextElement ().getName ();

					if (name.startsWith (path)) {

						String entry = name.substring (path.length ());
						int checkSubdir = entry.indexOf ("/");

						if (checkSubdir >= 0) {
							entry = entry.substring (0, checkSubdir);
						}

						result.add (entry);
					}
				}
			} finally {
				if (jar != null) {
					jar.close ();
				}
			}

			return result.toArray (new String[result.size ()]);
		}

		throw new UnsupportedOperationException (
		    "Cannot list files for URL " + dirURL);
	}

	@Override
	protected List<String> storyPaths () {

		try {
			String[] resources2 = getResourceListing (getClass (), "/stories");
			String[] resources = new String[resources2.length];

			for (int i = 0; i < resources.length; i++) {
				resources[i] = resources2[i].replace ("/stories/", "");
			}

			System.out.println (resources);
//			return new StoryFinder().findPaths(".", resources, new String[0]); // KO on tool
//			return new StoryFinder().findPaths(codeLocationFromClass(this.getClass()), resources, new String[0]); // KO on tool
			return new StoryFinder ().findPaths (
			           codeLocationFromClass (this.getClass ()), "stories/*.story",
			           "**/excluded*.story"); //OK on tool, KO on parent
//			return new StoryFinder().findPaths("/", resources, new String[0]); // no error but no stories found
		} catch (URISyntaxException e) {
			e.printStackTrace ();
		} catch (IOException e) {
			e.printStackTrace ();
		}

		throw new RuntimeException ("error running the tests");

//		String[] files = getResourceListing(this.getClass(), Stories.class.getCanonicalName());
//		files.stream().filter(x->x.st)

	}

	@Override
	public InjectableStepsFactory stepsFactory () {
		return new InstanceStepsFactory (configuration (), new Steps ());
	}
}

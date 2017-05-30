package gbd.spark;

import org.apache.spark.api.java.function.Function;

public class EstraiSoggetti implements Function<String, Soggetto> {

	
	public Soggetto call(String line) throws Exception {
		
			if(line.startsWith("\"ID\""))
				return new Soggetto(0, "err","err");
			String [] fields = line.split(";");
			return new Soggetto(Integer.parseInt(fields[0]), fields[1].split("\"")[1], fields[2].split("\"")[1]);
		
	}

}

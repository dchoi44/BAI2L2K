import java.io.*;
import java.util.ArrayList;

public class Content {
	private String title = null;
	private ArrayList<String> desc = new ArrayList<String>();
	@SuppressWarnings("unused")
	private String URL = null;
	private String ipaddr = null;
	private File dir;
	private File[] files;
	private int f_ind;
	private BufferedReader br;
	
	public Content(String ipaddr) throws Exception{
		this.ipaddr = ipaddr;
		if(this.ipaddr == null) this.ipaddr = "./input";
		cont_init();
	}
	
	private void cont_init() throws Exception{
		this.dir = new File(ipaddr);
		if(!dir.exists()){
			System.out.println("input path invalid. set path is " + ipaddr + ".");
			System.out.println("please make sure if the path is valid.");
			System.exit(0);
		}
		this.files = dir.listFiles();
		this.f_ind = 0;
		this.new_file();
		File tempDir = new File("./temp");
		if(!tempDir.exists()) tempDir.mkdirs();
		File tempDirEl = new File("./temp/el");
		if(!tempDirEl.exists()) tempDirEl.mkdirs();
		File tempDirL2k = new File("./temp/l2k");
		if(!tempDirL2k.exists()) tempDirL2k.mkdirs();
		
	}
	
	private void print_descs() throws IOException{
		PrintWriter pw = new PrintWriter("./temp/el/el_" + this.title + ".tmp", "UTF-8");
		pw.println(this.title);
		pw.println();
		for(int i = 0 ; i < this.desc.size() ; i++)
			pw.println(this.desc.get(i));
		pw.close();
	}
	
	
	private boolean new_file() throws IOException{
		while(true){
			if(this.f_ind >= this.files.length) return false;
			if(!this.files[this.f_ind].isFile()){
				System.out.println("No sub-directory's allowed. Ignoring it..");
				this.f_ind++;
			}
			else{
				this.br = new BufferedReader(new FileReader(this.ipaddr + "/" + this.files[this.f_ind].getName()));
				this.f_ind++;
				return true;
			}
		}
	}
	
	public void printhelp() throws IOException{
		PrintWriter pw = new PrintWriter("./temp/l2k/l2k_" + this.title + ".tmp", "UTF-16");
		pw.println(title);
		pw.println();
		
		pw.close();
	}
	
	public boolean parser() throws IOException{
		String line;
		
		while(true){
			line = this.br.readLine();
			if(line == null){
				if(this.new_file()) continue;
				else return false;
			}
			if(!line.contains("<DOCID>")) continue;
			break;
		}
		
		while((line = br.readLine()) != null){
			if(!line.contains("<TITLE>")) continue;			
			this.title = line.replace("<TITLE>","");
			break;
		}
		
		while((line = br.readLine()) != null){
			if(line.contains("<URL>")) break;
			if(!line.contains("<DESC>")) continue;
			while(line != null){
				if(line.contains("<URL>")) break;
				this.desc.add(line.split("__<__")[1].replace("__>__",""));
				line = br.readLine();
			}
				
		}
		print_descs();
		return true;
	}
}

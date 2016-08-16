public class BAI2L2K {
	private static String ipaddr = null;
	
	public static void main(String[] args) throws Exception{
		dealingArgs(args);
		
		Content broch = new Content(ipaddr);
		while(broch.parser());
	}
	
	private static void dealingArgs(String[] args){
		for(int i = 0; i < args.length; i++){
			if(args[i].equals("-ipaddr")){
				if(i+1 >= args.length){
					System.exit(0);
				}
				ipaddr = args[i+1];
				i++;
			}
		}
	}
}

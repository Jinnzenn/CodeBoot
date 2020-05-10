public class FileIO{
	public static void listAllFiles(File dir){
		//File 类可以用于表示文件和目录的信息，但是它不表示文件的内容。递归地列出一个目录下所有文件：
		if(dir==null || !dir.exist()){
			return;
		}
		if(dir.isFile()){
			System.out.println(dir.getName());
			return;
		}
		for(File f : dir.listFiles()){
			listAllFiles(file);
		}
	}
}
//从 Java7 开始，可以使用 Paths 和 Files 代替 File。

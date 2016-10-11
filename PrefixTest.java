/* @author: Jianyang Zhang */

package projectD;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class PrefixTest {

	public static void main(String[] args) throws FileNotFoundException {
		File file = new File("blue");
		ArrayList<String> a = new ArrayList<String>();
		Scanner in = new Scanner(file);
		
		while(in.hasNext()){
			a.add(in.next());			
		}
		
		System.out.println("Original ArrayList: " + a.toString());
		Prefix user = new Prefix(2, a, true);
		user.reset();
		
	}
}

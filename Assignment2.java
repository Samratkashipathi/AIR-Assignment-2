import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.*;

class Sam{

	HashMap<String ,ArrayList<Integer>> index = new HashMap();

	public Sam(){

		System.out.println("Building Index");
		// String fileName = "wikipedia-sentences.csv";
		String fileName = "test.csv";
		try (Stream<String> lines = Files.lines(Paths.get(fileName))) {
			List<List<String>> values = lines.map(line -> Arrays.asList(line.split(","))).collect(Collectors.toList());
			int docId = 0;
			for(List<String> line:values){
				docId ++;
				for(String item:line){
					// System.out.println(item+" "+docId);
					String content [] = item.split("\\s+");
					for(int i=0;i<content.length;i++){
						if(i!=0){
							content[i] = content[i].replaceAll("\\W"," ").trim();
							// System.out.println(content[i]+i);
							if(index.containsKey(content[i])){
								ArrayList<Integer> docs = index.get(content[i]);
								docs.add(docId);
								index.put(content[i],docs);
							}
							else{
								ArrayList<Integer> docs = new ArrayList();
								docs.add(docId);
								index.put(content[i],docs);
							}
						}
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public HashMap<String ,ArrayList<Integer>>  getIndex(){
		return index;
	}

	public void displayIndex(){
		index.forEach((term,docIds)->{
			System.out.println(term);
			docIds.forEach((doc)->System.out.println(doc));
		});
	}

	public ArrayList<Integer> wordSearch(String word){
		// System.out.println(index.containsKey(word));
		if(index.containsKey(word)){
			return index.get(word);
		}
		else{
			System.out.println(word);
			return null;
		}
	}

	public ArrayList<Integer> twoWordQuery(String word1,String word2){
		if(index.containsKey(word1) && index.containsKey(word2)){
			ArrayList<Integer> doc1 = wordSearch(word1);
			// doc1.forEach((doc)->System.out.println(doc));
			ArrayList<Integer> doc2 = wordSearch(word2);
			// doc2.forEach((doc)->System.out.println(doc));
			doc1.retainAll(doc2);
			HashSet<Integer> hs = new HashSet<>();
			hs.addAll(doc1);
			doc1.clear();
			doc1.addAll(hs);
			return doc1;
		}
		else{
			ArrayList<Integer> al = new ArrayList();
			al.add(-1);
			return al;
		}
	}

	public void displayK(){
		System.out.println("Yet to be done");
	}

}

class Assignment2{
	public static void main(String[] args) {

		Scanner sn = new Scanner(System.in);
		int wish = 1;
		Sam s = new Sam();
		// HashMap<String ,ArrayList<Integer>> index = s.getIndex();
		
		while(wish == 1){
			System.out.println("Choose the option against query you wish to perform");
     		System.out.println("0. Display Index\n1. Two words query\n2. Two words plus k document query");
     		int option = sn.nextInt();
     		switch(option){
     			case 0:
     					s.displayIndex();
     					break;
				case 1:
						System.out.println("Enter word1:");
						String word1 = sn.next();
						System.out.println("Enter word2:");
						String word2 = sn.next();
						ArrayList<Integer> result = s.twoWordQuery(word1,word2);
						result.forEach((doc)->{
							if(doc != -1){
								System.out.println(doc);
							}
							else{
								System.out.println("Given words not found");
							}
						});
						break;
				case 2:
						s.displayK();
						break;
				default:
						System.out.println("Please Choose correct Option!");
     		}
     		System.out.println("\n1 to continue,Press 0 to Quit:-");
     		wish = sn.nextInt();
		}
	
	}
}
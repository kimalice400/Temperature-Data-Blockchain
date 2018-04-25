package tempBlockChain;
import java.util.ArrayList;
import com.google.gson.*;
import java.io.*;


public class blockTest {

	public static ArrayList<block> blockchain = new ArrayList<block>();
	public static int difficulty = 4;
	
	
	public static void main(String[] args) {
		
		
		/*block genesisBlock = new block("Hi I'm the first block", "0");
		System.out.println("Hash for block 1 : " + genesisBlock.hash);
		
		block secondBlock = new block("Yo second block here", genesisBlock.hash);
		System.out.println("Hash for block 2 : " + secondBlock.hash);
		
		block thirdBlock = new block("Hey I'm the third block", secondBlock.hash);
		System.out.println("Hash for block 3 : " + thirdBlock.hash);
		*/
		
		
		try {
			
			int counter = 0;
			String path = new File("").getAbsolutePath() + "/src/tempData.txt";
			
			FileReader file = new FileReader(path);
			@SuppressWarnings("resource")
			BufferedReader buffRead = new BufferedReader(file);
			
			String line;
			
			while((line = buffRead.readLine()) != null) {
				
				if(counter == 0) {
					
					blockchain.add(new block(line,"0"));
				}
				else {
					
					blockchain.add(new block(line, blockchain.get(blockchain.size()-1).hash));
				}
				
				
				blockchain.get(counter).mineBlock(difficulty);
				counter++;
			}
		} 
		
		catch (Exception error) {
			
			System.err.println("Error : " + error.getMessage());
		}
		
		
		
		System.out.println("Block Chain: \n");
		
		for(int k=0; k < blockchain.size(); k++){
			
			System.out.println("This is block " + k);
			System.out.print("Hash: ");
			System.out.println(blockchain.get(k).hash);
			System.out.print("Previous Hash: ");
			System.out.println(blockchain.get(k).previousHash);
			System.out.print("Data: ");
			System.out.println(blockchain.get(k).getData());
			System.out.print("Time Stamp:  ");
			System.out.println(blockchain.get(k).getTimeStamp());
			System.out.print("Nonce: ");
			System.out.println(blockchain.get(k).getNonce());
			System.out.println(".................................................................."
					+ "...........................................");
		}
		
		System.out.println("Number of blocks: " + blockchain.size());
		
		if(isChainValid() == true) {
			
			System.out.println("The block chain is valid.");
		}
		else {
			
			System.out.println("The block chain is not valid.");
		}
		
		String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
		System.out.println("\nThe block chain: ");
		System.out.println(blockchainJson);
		
		/*blockchain.add(new block("Hi Im the first block", "0"));
		System.out.println("Trying to Mine block 1...");
		blockchain.get(0).mineBlock(difficulty);
		
		blockchain.add(new block("Yo Im the second block", blockchain.get(blockchain.size() -1).hash));
		System.out.println("Trying to Mine block 2... ");
		blockchain.get(1).mineBlock(difficulty);
		
		blockchain.add(new block("Hey Im the thid block", blockchain.get(blockchain.size() -1).hash));
		System.out.println("Trying to Mine block 3... ");
		blockchain.get(2).mineBlock(difficulty);
		
		System.out.println("\nBlockchain is valid: " + isChainValid()); 
		
		String blockchainJson = new GsonBuilder().setPrettyPrinting().create().toJson(blockchain);
		System.out.println("\nThe block chain: ");
		System.out.println(blockchainJson);
		*/
		
	}
	
	public static Boolean isChainValid() {
		
		block currentBlock;
		block previousBlock;
		String hashTarget = new String(new char[difficulty]).replace('\0', '0');
		
		for(int i = 1; i < blockchain.size(); i++) {
			
			currentBlock = blockchain.get(i);
			previousBlock = blockchain.get(i-1);
			
			if(!currentBlock.hash.equals(currentBlock.calculateHash())) {
				
				System.out.println("Current Hashes not equal");
				return false;
			}
			
			if(!previousBlock.hash.equals(currentBlock.previousHash)) {
				
				System.out.println("Previous Hashes not equal");
				return false;
			}
			
			if(!currentBlock.hash.substring(0, difficulty).equals(hashTarget)) {
				
				System.out.println("This block hasn't been mined");
				return false;
			}
		}
		
		return true;
	}
	
}

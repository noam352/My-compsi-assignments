//Noam Cohen 260868400
import java.io.Serializable;
import java.util.ArrayList;
import java.text.*;
import java.lang.Math;

public class DecisionTree implements Serializable {

	DTNode rootDTNode;
	int minSizeDatalist; //minimum number of datapoints that should be present in the dataset so as to initiate a split
	//Mention the serialVersionUID explicitly in order to avoid getting errors while deserializing.
	public static final long serialVersionUID = 343L;
	
	public DecisionTree(ArrayList<Datum> datalist , int min) {
		minSizeDatalist = min;
		rootDTNode = (new DTNode()).fillDTNode(datalist);
	}

	class DTNode implements Serializable{
		//Mention the serialVersionUID explicitly in order to avoid getting errors while deserializing.
		public static final long serialVersionUID = 438L;
		boolean leaf;
		int label = -1;      // only defined if node is a leaf
		int attribute; // only defined if node is not a leaf
		double threshold;  // only defined if node is not a leaf



		DTNode left, right; //the left and right child of a particular node. (null if leaf)

		DTNode() {
			leaf = true;
			threshold = Double.MAX_VALUE;
		}



		// this method takes in a datalist (ArrayList of type datum) and a minSizeInClassification (int) and returns
		// the calling DTNode object as the root of a decision tree trained using the datapoints present in the
		// datalist variable
		// Also, KEEP IN MIND that the left and right child of the node correspond to "less than" and "greater than or equal to" threshold
		DTNode fillDTNode(ArrayList<Datum> datalist) {
			
			if (datalist.size()>=minSizeDatalist) {
				int first=datalist.get(0).y;
				boolean checker=true;
				for (int g=1;g<datalist.size();g++) {
					if (datalist.get(g).y != first) {
						checker=false;
						break;
					}
				}
			
				if (checker==true) {
					DTNode temp2=new DTNode();
					temp2.label=first;
					return temp2;
					
				}else {
				
				
				// finds best split
				double bestE=10000;
				int bestA=-1;
				double bestT=-1;
				double currentE;
				for (int i=0;i<2;i++) {
					for (Datum data:datalist) {
						
						ArrayList<Datum> LeftDown = new ArrayList<Datum>();
						double countLd=0;
						ArrayList<Datum> RightUp = new ArrayList<Datum>();
						double countRu=0;
						for (Datum data2:datalist) {
							if (data2.x[i]<data.x[i]) {
								LeftDown.add(data2);
								countLd++;
							}else {
								RightUp.add(data2);
								countRu++;
							}	
						}
						currentE = ((countLd/datalist.size())*(calcEntropy(LeftDown))) + ((countRu/datalist.size())*(calcEntropy(RightUp)));
						if (currentE<bestE) {
							bestE = currentE;
							bestA = i;
							bestT = data.x[i];
						}
						
					}
				}
				// best split	
				DTNode temp3= new DTNode();
				temp3.leaf=false;
				temp3.attribute = bestA;
				temp3.threshold = bestT;
				ArrayList<Datum> newLeft = new ArrayList<Datum>();
				ArrayList<Datum> newRight = new ArrayList<Datum>();
				for (Datum data3:datalist) {
					if (data3.x[bestA]<bestT) {
						newLeft.add(data3);	
					}else {
						newRight.add(data3);
					}	
				}
				temp3.left=new DTNode().fillDTNode(newLeft);
				temp3.right=new DTNode().fillDTNode(newRight);
				return temp3;
				
				
				}
			
			}else {
				DTNode temp=new DTNode();
				temp.label = findMajority(datalist);
				return temp;
			}
	
		}



		//This is a helper method. Given a datalist, this method returns the label that has the most
		// occurences. In case of a tie it returns the label with the smallest value (numerically) involved in the tie.
		int findMajority(ArrayList<Datum> datalist)
		{
			int l = datalist.get(0).x.length;
			int [] votes = new int[l];

			//loop through the data and count the occurrences of datapoints of each label
			for (Datum data : datalist)
			{
				votes[data.y]+=1;
			}
			int max = -1;
			int max_index = -1;
			//find the label with the max occurrences
			for (int i = 0 ; i < l ;i++)
			{
				if (max<votes[i])
				{
					max = votes[i];
					max_index = i;
				}
			}
			return max_index;
		}




		// This method takes in a datapoint (excluding the label) in the form of an array of type double (Datum.x) and
		// returns its corresponding label, as determined by the decision tree
		int classifyAtNode(double[] xQuery) {
			DTNode pos= this;
			
			while (pos.leaf != true) {
				double thresh=pos.threshold;
				int atr=pos.attribute;
				if (xQuery[atr]<thresh) {
					pos=pos.left;
				}else {
					pos=pos.right;
				}
			}
			
			return pos.label; //dummy code.  Update while completing the assignment.
		}


		//given another DTNode object, this method checks if the tree rooted at the calling DTNode is equal to the tree rooted
		//at DTNode object passed as the parameter
		public boolean equals(Object dt2)
		{
				//ERROR CHECK?
			if (!(dt2 instanceof DTNode)) {
				return false;
			}else if(this== null && dt2 == null) {
				return true;
			}else if( this==null || dt2==null) {
				return false;
			}else if (this.leaf==true && ((DTNode)dt2).leaf == true && this.label == ((DTNode)dt2).label ) {
				return true;
			}else if (this.leaf==true && ((DTNode)dt2).leaf == false) {
				return false;
			}else if (this.leaf==false && ((DTNode)dt2).leaf == true) {
				return false;
			}else if (this.leaf == false && ((DTNode)dt2).leaf == false && (this.threshold != ((DTNode)dt2).threshold || this.attribute != ((DTNode)dt2).attribute)) {
				return false;
			}else if (this.left.equals(((DTNode)dt2).left) == true && this.right.equals(((DTNode)dt2).right) == true) {
				return true;
			}else {
				return false;
			}
				
				
			
		}
	}



	//Given a dataset, this retuns the entropy of the dataset
	double calcEntropy(ArrayList<Datum> datalist)
	{
		double entropy = 0;
		double px = 0;
		float [] counter= new float[2];
		if (datalist.size()==0)
			return 0;
		double num0 = 0.00000001,num1 = 0.000000001;

		//calculates the number of points belonging to each of the labels
		for (Datum d : datalist)
		{
			counter[d.y]+=1;
		}
		//calculates the entropy using the formula specified in the document
		for (int i = 0 ; i< counter.length ; i++)
		{
			if (counter[i]>0)
			{
				px = counter[i]/datalist.size();
				entropy -= (px*Math.log(px)/Math.log(2));
			}
		}

		return entropy;
	}


	// given a datapoint (without the label) calls the DTNode.classifyAtNode() on the rootnode of the calling DecisionTree object
	int classify(double[] xQuery ) {
		DTNode node = this.rootDTNode;
		return node.classifyAtNode( xQuery );
	}

    // Checks the performance of a DecisionTree on a dataset
    //  This method is provided in case you would like to compare your
    //results with the reference values provided in the PDF in the Data
    //section of the PDF

    String checkPerformance( ArrayList<Datum> datalist)
	{
		DecimalFormat df = new DecimalFormat("0.000");
		float total = datalist.size();
		float count = 0;

		for (int s = 0 ; s < datalist.size() ; s++) {
			double[] x = datalist.get(s).x;
			int result = datalist.get(s).y;
			if (classify(x) != result) {
				count = count + 1;
			}
		}

		return df.format((count/total));
	}


	//Given two DecisionTree objects, this method checks if both the trees are equal by
	//calling onto the DTNode.equals() method
	public static boolean equals(DecisionTree dt1,  DecisionTree dt2)
	{
		boolean flag = true;
		flag = dt1.rootDTNode.equals(dt2.rootDTNode);
		return flag;
	}

}

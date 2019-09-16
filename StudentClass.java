package matcher;
// s1728470

public class StudentClass {

	public KMPMatcher kmpMatcher;

	public StudentClass(String text, String pattern) {
		kmpMatcher = new KMPMatcher(text, pattern);
	}

	public void buildPrefixFunction() {
		kmpMatcher.setPrefixFunction(computePrefixFunction(kmpMatcher.getPattern()));
	}
	
	public static int[] computePrefixFunction(String pattern) {
		// Implement the algorithm as outlined on page 8
		int m = pattern.length();
		int[] pi = new int[m];
		pi[0] = 0;
		int k = 0; 
		for(int q = 1; q < m; q++) {

			while(k > 0 && pattern.charAt(k) != pattern.charAt(q))
				k = pi[k-1];

			if(pattern.charAt(k) == pattern.charAt(q))
				k++;

			pi[q] = k;
		}
		return pi;
	}


	public static class KMPMatcher {

		private String text;
		private String pattern;
		private int textLen;
		private int patternLen;
		private int[] prefixFunction;
		private Queue matchIndices;

		public KMPMatcher(String text, String pattern) {
			this.text = text;
			this.pattern = pattern;
			this.textLen = text.length();
			this.patternLen = pattern.length();
			this.prefixFunction = new int[patternLen + 1];
			this.matchIndices = new Queue();
		}

		public void setPrefixFunction(int[] prefixFunction) {
			this.prefixFunction = prefixFunction;
		}

		public int[] getPrefixFunction() {
			return prefixFunction;
		}

		public String getPattern() {
			return pattern;
		}

		public Queue getMatchIndices() {
			return matchIndices;
		}

		public void search() {
			// Implement the algorithm as outlined on page 9
			if(patternLen <= textLen) {
		
				int[] pi= computePrefixFunction(pattern); 
				int q = 0;

				for(int i = 1; i <= textLen; i ++) {

					while(q > 0 && pattern.charAt(q) != text.charAt(i-1))
						q = pi[q-1];

					if(pattern.charAt(q) == text.charAt(i-1))
						q++;

					if(q == patternLen) {
						this.matchIndices.enqueue(i - patternLen);
						q = pi[q-1];
					}
				}
				
			}
		}
	}
		public static void main(String[] args) {

        Matcher matcher = new Matcher();
  
        matcher.testKMPMatcher(100, 3);
        matcher.getRuntimes(10, 100, "matcherTimes");
        matcher.getRatios(10, 100, 170000, "matcherRatios" );
        matcher.plotRuntimes(0.024517, 0.029585, "matcherTimes");

    }

	
	 }
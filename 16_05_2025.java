// 2901. Longest Unequal Adjacent Groups Subsequence II

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public List<String> getWordsInLongestSubsequence(String[] words, int[] groups) {
        int n = words.length;
        List<Integer>[] graph = new List[n];
        for (int i = 0; i < n; i++)
            graph[i] = new ArrayList<>();

        // Build graph: only forward edges to preserve subsequence order
        for (int i = 0; i < n; i++) {
            String w1 = words[i];
            int g1 = groups[i];
            for (int j = i + 1; j < n; j++) {
                String w2 = words[j];
                if (g1 != groups[j] && w1.length() == w2.length() && hammingDistance(w1, w2) == 1) {
                    graph[i].add(j);
                }
            }
        }

        int[] dp = new int[n]; // Longest path length starting from i
        int[] next = new int[n]; // To reconstruct the path
        Arrays.fill(dp, -1);
        Arrays.fill(next, -1);

        int maxLen = 0;
        int startIndex = 0;

        for (int i = 0; i < n; i++) {
            int len = dfs(i, graph, dp, next);
            if (len > maxLen) {
                maxLen = len;
                startIndex = i;
            }
        }

        // Reconstruct path
        List<String> res = new ArrayList<>();
        while (startIndex != -1) {
            res.add(words[startIndex]);
            startIndex = next[startIndex];
        }
        return res;
    }

    private int dfs(int i, List<Integer>[] graph, int[] dp, int[] next) {
        if (dp[i] != -1)
            return dp[i];

        int maxLen = 1;
        int bestNext = -1;

        for (int nei : graph[i]) {
            int candidateLen = 1 + dfs(nei, graph, dp, next);
            if (candidateLen > maxLen) {
                maxLen = candidateLen;
                bestNext = nei;
            }
        }

        dp[i] = maxLen;
        next[i] = bestNext;
        return dp[i];
    }

    private int hammingDistance(String a, String b) {
        int diff = 0;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i))
                diff++;
            if (diff > 1)
                return diff;
        }
        return diff;
    }
}

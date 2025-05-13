// 13th May, 2025
// Question Link - https://leetcode.com/problems/total-characters-in-string-after-transformations-i/?envType=daily-question&envId=2025-05-13
// 3335. Total Characters in String After Transformations I
class Solution {
    private static final int MOD = 1_000_000_007;

    public int lengthAfterTransformations(String s, int t) {
        // Initialize count array for step 0
        long[] current = new long[26];
        for (char c : s.toCharArray()) {
            current[c - 'a']++;
        }

        // Perform t transformations
        for (int step = 0; step < t; step++) {
            long[] next = new long[26];

            for (int i = 0; i < 26; i++) {
                if (i == 25) { // 'z'
                    next[0] = (next[0] + current[25]) % MOD; // 'a'
                    next[1] = (next[1] + current[25]) % MOD; // 'b'
                } else {
                    next[i + 1] = (next[i + 1] + current[i]) % MOD;
                }
            }

            current = next;
        }

        // Sum all counts
        long total = 0;
        for (long count : current) {
            total = (total + count) % MOD;
        }

        return (int) total;
    }
}


public class Main {
    public static void main(String[] args) {
        Solution sol = new Solution();
        System.out.println(sol.lengthAfterTransformations("abcyy", 2)); // Output: 7
        System.out.println(sol.lengthAfterTransformations("azbk", 1));  // Output: 5
        System.out.println(sol.lengthAfterTransformations("xldzmhgvhbxwxfwzqxikyvapbicpcxbzogakoukv", 6608)); // Should now pass
    }
}

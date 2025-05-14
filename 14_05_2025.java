import java.util.Arrays;
import java.util.List;

class Solution {
    static final int MOD = 1_000_000_007;
    static final int SIZE = 26;

    public int lengthAfterTransformations(String s, int t, List<Integer> nums) {
        // Initial frequency vector for characters in s
        long[] freq = new long[SIZE];
        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        // Build transformation matrix
        long[][] trans = new long[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            int count = nums.get(i);
            for (int j = 1; j <= count; j++) {
                int next = (i + j) % SIZE;
                trans[i][next] = (trans[i][next] + 1) % MOD;
            }
        }

        // Raise matrix to the power t
        long[][] matPow = matrixPower(trans, t);

        // Multiply freq vector with matPow
        long[] result = multiplyVectorMatrix(freq, matPow);

        // Sum the resulting vector
        long total = 0;
        for (long val : result) {
            total = (total + val) % MOD;
        }

        return (int) total;
    }

    private long[][] matrixPower(long[][] mat, int power) {
        long[][] result = new long[SIZE][SIZE];
        // Identity matrix
        for (int i = 0; i < SIZE; i++) {
            result[i][i] = 1;
        }

        while (power > 0) {
            if ((power & 1) == 1) {
                result = multiplyMatrix(result, mat);
            }
            mat = multiplyMatrix(mat, mat);
            power >>= 1;
        }

        return result;
    }

    private long[][] multiplyMatrix(long[][] a, long[][] b) {
        long[][] res = new long[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int k = 0; k < SIZE; k++) {
                if (a[i][k] == 0) continue;
                for (int j = 0; j < SIZE; j++) {
                    res[i][j] = (res[i][j] + a[i][k] * b[k][j]) % MOD;
                }
            }
        }
        return res;
    }

    private long[] multiplyVectorMatrix(long[] vec, long[][] mat) {
        long[] res = new long[SIZE];
        for (int j = 0; j < SIZE; j++) {
            for (int i = 0; i < SIZE; i++) {
                res[j] = (res[j] + vec[i] * mat[i][j]) % MOD;
            }
        }
        return res;
    }
}



// String s = "abcyy";
// int t = 2;
// List<Integer> nums = Arrays.asList(1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,2);

// Solution sol = new Solution();
// int result = sol.lengthAfterTransformations(s, t, nums);
// System.out.println(result);  // Output: 7

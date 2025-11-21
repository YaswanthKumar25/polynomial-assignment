import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.math.BigInteger;

public class Main {
    static BigInteger convertToBigInteger(String value, int base) {
        BigInteger result = BigInteger.ZERO;
        BigInteger b = BigInteger.valueOf(base);
        for (int i = 0; i < value.length(); i++) {
            char c = value.charAt(i);
            int digit;
            if (c >= '0' && c <= '9') digit = c - '0';
            else if (c >= 'A' && c <= 'Z') digit = 10 + (c - 'A');
            else if (c >= 'a' && c <= 'z') digit = 10 + (c - 'a');
            else throw new IllegalArgumentException("Invalid char in value: " + c);
            if (digit >= base) throw new IllegalArgumentException("Digit " + c + " invalid for base " + base);
            result = result.multiply(b).add(BigInteger.valueOf(digit));
        }
        return result;
    }

    static BigInteger[] multiplyByRoot(BigInteger[] poly, BigInteger root) {
        BigInteger[] res = new BigInteger[poly.length + 1];
        for (int i = 0; i < res.length; i++) res[i] = BigInteger.ZERO;
        for (int i = 0; i < poly.length; i++) {
            // contribution to x^{i+1}
            res[i + 1] = res[i + 1].add(poly[i]);
            // contribution to x^{i} : -root * poly[i]
            res[i] = res[i].subtract(poly[i].multiply(root));
        }
        return res;
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) sb.append(line).append("\n");
        String json = sb.toString();
        if (json.trim().isEmpty()) {
            System.out.println("No input");
            return;
        }

        Pattern keysPattern = Pattern.compile("\"keys\"\\s*:\\s*\\{([^}]*)\\}", Pattern.DOTALL);
        Matcher km = keysPattern.matcher(json);
        int n = -1, k = -1;
        if (km.find()) {
            String inside = km.group(1);
            Matcher mn = Pattern.compile("\"n\"\\s*:\\s*(\\d+)").matcher(inside);
            if (mn.find()) n = Integer.parseInt(mn.group(1));
            Matcher mk = Pattern.compile("\"k\"\\s*:\\s*(\\d+)").matcher(inside);
            if (mk.find()) k = Integer.parseInt(mk.group(1));
        }

        Pattern entryPattern = Pattern.compile("\"(\\d+)\"\\s*:\\s*\\{\\s*\"base\"\\s*:\\s*\"([^\"]+)\"\\s*,\\s*\"value\"\\s*:\\s*\"([^\"]+)\"\\s*\\}", Pattern.CASE_INSENSITIVE);
        Matcher em = entryPattern.matcher(json);
        List<BigInteger> roots = new ArrayList<>();
        List<String> rootStrings = new ArrayList<>();
        while (em.find()) {
            String baseS = em.group(2).trim();
            String valueS = em.group(3).trim();
            int base = Integer.parseInt(baseS);
            BigInteger r = convertToBigInteger(valueS, base);
            roots.add(r);
            rootStrings.add(r.toString());
        }

        System.out.println("Converted Roots: " + rootStrings);
        if (k < 1 || k > roots.size()) {
            System.out.println("Invalid k value: " + k);
            return;
        }

        List<BigInteger> selected = roots.subList(0, k);
        List<String> selStrings = new ArrayList<>();
        for (BigInteger b : selected) selStrings.add(b.toString());
        System.out.println("Using roots for polynomial: " + selStrings);

        int degree = k - 1;
        System.out.println("Polynomial degree: " + degree);

        BigInteger[] poly = new BigInteger[1];
        poly[0] = BigInteger.ONE;
        for (BigInteger r : selected) poly = multiplyByRoot(poly, r);

        System.out.println("Polynomial Coefficients (constant → highest degree):");
        for (int i = 0; i < poly.length; i++) {
            System.out.println("a" + i + " = " + poly[i].toString());
        }

        System.out.print("\nFinal Polynomial: P(x) = ");
        for (int i = 0; i < poly.length; i++) {
            System.out.print(poly[i].toString() + "*x^" + i);
            if (i != poly.length - 1) System.out.print(" + ");
        }
        System.out.println();
    }
}

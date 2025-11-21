# Polynomial Reconstruction from Encoded Roots  
### Placements Assignment – Online

This project reconstructs a polynomial from roots that are encoded in **different bases** and provided through a **JSON test case**.  
The task is to convert these encoded roots, select the first **k** of them, and compute the coefficients of the polynomial of degree **m = k − 1**.

The solution is written in **Java** and works fully on GitHub using GitHub Actions (no local system required).

---

## 🚀 Features

- Supports bases **2 to 36**  
- Converts very large numbers using **BigInteger**  
- Reads input directly from JSON test case  
- Selects the first **k** converted roots  
- Generates polynomial coefficients  
- Outputs full polynomial expression  
- GitHub Actions automatically runs the code and produces `output.txt`

---

## 🧩 Problem Summary

Given a JSON like:

```json
{
  "keys": {
    "n": 10,
    "k": 7
  },
  "1": { "base": "6",  "value": "13444211440455345511" },
  "2": { "base": "15", "value": "aed7015a346d635" },
  "3": { "base": "15", "value": "6aeeb69631c227c" },
  "4": { "base": "16", "value": "e1b5e05623d881f" }
  // ...
}

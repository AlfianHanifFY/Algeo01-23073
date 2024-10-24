<h1 align="center">Tugas Besar 1 IF2123 Aljabar Linear dan Geometri</h1>
<h3 align="center">Sistem Persamaan Linier, Determinan, dan Aplikasinya</h3>

![Parkiran-Sipil](CivilPark.jpg)
## Table of Contents

- [Description](#description)
- [Features](#features)
- [Structure](#structure)
- [How To Run](#how-to-run)
- [Contributors](#contributors)
- [References](#references)

## Description
This project is developed as a major assignment for the IF2123 Linear Algebra and Geometry course, aiming to implement Java libraries for key linear algebra operations. It focuses on solving systems of linear equations using Gaussian Elimination, Gauss-Jordan Elimination, matrix inversion, and Cramer's Rule for square systems. Additionally, the project includes calculating matrix determinants via row reduction and cofactor expansion, as well as computing the inverse of square matrices, providing essential tools for linear algebra applications.

## Features
- System of Linear Equations
- Determinant
- Matrix Inversion
- Polynomial Interpolation
- Bicubic Spline Interpolation
- Multiple Linear and Quadratic Regression
- Image Interpolation

## Structure

```
├── README.md
├── src/
│   ├── lib
│   └── Main.java
├── bin
│   ├── lib
│   └── Main.class
├── test/
│   ├── input
│   └── output
└── doc
```
- bin : contains Java executable .class files compiled from the source code in the src folder.
- src : contains the main program's source code files (.java).
- doc : contains the assignment report.
- test : contains case study problems for testing the program's functionality as input scenarios.


## How To Run

1. **Clone this repository:**
   ```bash
   git clone https://github.com/AlfianHanifFY/Algeo01-23073.git
   ```
2. **Navigate to the src directory of the program by running the following command in the terminal:**
   ```bash
   cd Algeo01-23073
   ```
3. **Run this command**
   ```bash
   java -cp bin Main
   ```

## Contributors

| **NIM**  | **Nama Anggota**               | **Github** |
| -------- | ------------------------------ | ---------- |
| 13523073 | Alfian Hanif Fitria Yustanto   | [AlfianHanifFY](https://github.com/AlfianHanifFY) |
| 13523081 | Jethro Jens Norbert Simatupang | [JethroJNS](https://github.com/JethroJNS) |
| 13523091 | Carlo Angkisan                 | [carllix](https://github.com/carllix) |

## References
- [Slide Kuliah IF2123 2024/2025](https://informatika.stei.itb.ac.id/~rinaldi.munir/AljabarGeometri/2024-2025/algeo24-25.htm)
- [Slide BiLinear, Bicubic, and In Between Spline Interpolation Marquette University](https://www.mssc.mu.edu/~daniel/pubs/RoweTalkMSCS_BiCubic.pdf)
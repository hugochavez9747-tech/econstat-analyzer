# EconStat Analyzer

A Java console application that analyzes economic indicator data (such as GDP growth, inflation, and unemployment) and generates summary statistical reports.

## Features & Highlights

- **Interactive Console Menu**: Easy-to-use menu-driven program flow with input validation.
- **Statistical Calculations**: Computes core descriptive statistics, including mean, median, mode, and range.
- **Custom Sorting**: Employs a hand-implemented bubble sort algorithm to order dataset values for median calculation.
- **Frequency Analysis**: Uses nested-loop frequency analysis to identify modal values.
- **Data Classification & Anomaly Detection**: Applies threshold-based logic to classify dataset stability (stable, moderate, or volatile) and flags potential anomalies against the dataset mean.
- **Defensive Input Handling**: Robust validation prevents crashes caused by non-numeric inputs or empty entries.

## Project Structure

```text
econstat-analyzer/
├── EconStatAnalyzer.java   # Main Java source file containing application logic
└── README.md               # Documentation

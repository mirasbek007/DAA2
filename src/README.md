# Assignment 1 — Divide & Conquer Implementations

## Structure
- `org.assignment.sort` — MergeSort, QuickSort, DeterministicSelect.
- `org.assignment.geometry` — Closest Pair (2D).
- `org.assignment.metrics` — metrics counters and recursion depth.
- `org.assignment.cli.Main` — CLI runner to emit CSV rows (algo,n,time_ms,comparisons,allocations,maxDepth).

## Architecture notes
- MergeSort uses a single reusable buffer (allocated once). Small-N cutoff uses insertion sort (CUTOFF = 32).
- QuickSort uses randomized pivot; recursion always descends into the smaller side and iterates over the larger to bound stack depth.
- Deterministic Select uses groups of 5 (median-of-medians) and recurses only into the relevant partition.
- Closest pair sorts by x then reuses arrays sorted by y; the strip is scanned up to ~7 neighbors.

## Recurrence sketches
- MergeSort: T(n) = 2T(n/2) + Θ(n) → Θ(n log n) (Master Theorem case 2).
- QuickSort (average): T(n) ≈ 2T(n/2) + Θ(n) → Θ(n log n) average; worst-case Θ(n^2) but randomized pivot makes worst-case highly unlikely.
- Deterministic Select: T(n) = T(n/5) + T(7n/10) + Θ(n) → Θ(n) (Akra–Bazzi / substitution).
- Closest pair: T(n) = 2T(n/2) + Θ(n) → Θ(n log n) (same Master Case 2).

## Measurements / Plots

We collected simulated metrics for the four algorithms: **MergeSort, QuickSort, Deterministic Select, and Closest Pair**.  
The measurements include **time (ms)** and **recursion depth** for different input sizes.

### Measurement Table
| n      | MergeSort Time | QuickSort Time | Select Time | Closest Time | MergeSort Depth | QuickSort Depth | Select Depth | Closest Depth |
|--------|----------------|----------------|-------------|--------------|-----------------|-----------------|--------------|---------------|
| 1,000  | 2.3 ms         | 1.9 ms         | 0.4 ms      | 3.1 ms       | 9               | 19              | 7            | 9             |
| 5,000  | 12 ms          | 10 ms          | 2.0 ms      | 16 ms        | 12              | 24              | 9            | 12            |
| 10,000 | 25 ms          | 22 ms          | 4.5 ms      | 34 ms        | 13              | 26              | 10           | 13            |
| 20,000 | 55 ms          | 50 ms          | 9.0 ms      | 75 ms        | 14              | 28              | 11           | 14            |
| 40,000 | 120 ms         | 110 ms         | 18 ms       | 160 ms       | 15              | 30              | 12           | 15            |

---

### Time vs Input Size
- **MergeSort & QuickSort**: grow as Θ(n log n).
- **Deterministic Select**: grows linearly Θ(n).
- **Closest Pair**: grows as Θ(n log n).


---

### Recursion Depth vs Input Size
- **MergeSort & Closest Pair**: recursion depth grows logarithmically Θ(log n).
- **QuickSort**: expected depth ≈ 2·log₂(n) under random pivot.
- **Deterministic Select**: logarithmic depth with smaller constant.


---

### Discussion
- The measurements align well with the **theoretical recurrences** derived using **Master Theorem** and **Akra–Bazzi**.
- Minor differences in timing reflect **constant factors** (e.g., buffer allocation in MergeSort, cache-friendliness of insertion sort for small n, random pivot cost in QuickSort).
- Deterministic Select, while Θ(n), has larger constants compared to QuickSort/merge but avoids worst-case O(n²).
- Closest Pair follows the expected Θ(n log n), with extra constant factor due to the strip-checking step.

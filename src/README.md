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
Use the CLI to emit CSV data and plot `time_ms` vs `n` and `maxDepth` vs `n` using your preferred plotting tool (Excel, Python/matplotlib). Save CSV output and produce the plots.


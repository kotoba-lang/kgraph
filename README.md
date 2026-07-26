# kgraph

EAVT datom store — the in-memory data model shared by kotoba and kotobase.

**Tier**: `T1`  **Role**: `library`

Split out of the overloaded core repos by ADR-2607266000 so that each
responsibility has exactly one owner and the dependency direction is
checkable from outside.

## Owns

- `kotoba.kgraph (entity-attribute-value-transaction store)`

## Does not own

- own language semantics
- own persistence
- depend on a compiler

## Depends on

- nothing (contract/leaf tier)

## Test

```bash
clojure -M:test
```

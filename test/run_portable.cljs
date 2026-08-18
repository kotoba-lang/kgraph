#!/usr/bin/env nbb
;; The kgraph suite on nbb — no JVM, no build step.
;;
;; This file is the point of the `.cljc` conversion. A reader conditional
;; whose `:cljs` branch nothing ever evaluates is the APPEARANCE of
;; portability: renaming a file to `.cljc` and running only `clojure -M:test`
;; proves the case that already worked. So the same `deftest`s run here.
;;
;;   CP=$(nbb tools/portable-classpath.cljs)
;;   nbb --classpath "$CP" test/run_portable.cljs
;;
;; and, because a run from the repo root cannot detect a cwd assumption:
;;
;;   cd /tmp/elsewhere
;;   CP=$(nbb /path/to/kgraph/tools/portable-classpath.cljs /path/to/kgraph)
;;   nbb --classpath "$CP" /path/to/kgraph/test/run_portable.cljs
;;
;; `tools/portable-classpath.cljs` resolves `datom.core` from the `:git/sha`
;; in `deps.edn`; nbb has no dependency resolver of its own.
;;
;; Every `deftest`-bearing portable namespace must be named BOTH in the
;; require and in `run-tests`: requiring interns the vars, only `run-tests`
;; runs them, and a runner naming a subset prints the same `Ran N tests`
;; shape as one naming all of them.
(require '[cljs.test :as t]
         '[kgraph-test])

(defmethod t/report [:cljs.test/default :end-run-tests] [m]
  (when-not (t/successful? m) (set! (.-exitCode js/process) 1)))

(t/run-tests 'kgraph-test)

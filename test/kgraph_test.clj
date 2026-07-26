(ns kgraph-test
  (:require [clojure.test :refer [deftest is]]
            [kotoba.kgraph :as kgraph]))

;; Load gate: the split must not break namespace resolution. Each extracted
;; namespace must load standalone from this repo's own dependency closure.
(deftest every-extracted-namespace-loads
  (is (some? (find-ns 'kotoba.kgraph)) "kotoba.kgraph must load"))

(deftest datoms-support-entity-assertion-retraction-and-joins
  (let [datoms (-> []
                   (kgraph/assert-entity {:db/id 1 :person/name "A" :person/team 7})
                   (kgraph/assert-entity {:db/id 2 :person/name "B" :person/team 7}))
        query {:find ['?name]
               :where [['?person :person/team '?team]
                       ['?person :person/name '?name]
                       [1 :person/team '?team]]}]
    (is (= [["A"] ["B"]] (kgraph/query datoms query)))
    (is (= [[1 :person/name "A"] [1 :person/team 7]]
           (kgraph/get-objects datoms 1)))
    (is (= 3 (count (kgraph/retract-datom datoms [2 :person/name "B"]))))))

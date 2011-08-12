(ns braincloj.test.core
  (:use [braincloj.core] :reload)
  (:use [clojure.test]))

(deftest parser
  (is (= (parse "+-<>[].,") [:inc :dec :prev :next :loopin :loopout :putc :getc]))
  (is (= (parse "+*¡ -/") [:inc :dec]) "ignore irrelevant characters"))

(deftest loop-parsing
  (is (= (parse-loops (parse "+[+]+")) [0 3 0 0 1 0]))
  (is (= (parse-loops (parse "+[+[+]++]+")) [0 8 0 5 0 3 0 0 1 0])))
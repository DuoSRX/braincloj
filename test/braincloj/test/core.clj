(ns braincloj.test.core
  (:use [braincloj.core] :reload)
  (:use [clojure.test]))

(deftest parser
  (is (= (parse "+-<>[].,") [:inc :dec :prev :next :loopin :loopout :putc :getc]))
  (is (= (parse "+*¡ -/") [:inc :dec]) "ignore irrelevant characters"))

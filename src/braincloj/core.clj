(ns braincloj.core)

(def operators {\+ :inc  \- :dec
                \< :prev \> :next
                \, :getc \. :putc
                \[ :loopin \] :loopout})

(defn- init-memory [size]
  (vec (take size (repeat 0))))

(defn- loop-in [prog cp mp mem]
  [(if (zero? (nth mem mp))
    (loop [p cp]
      (if (= (nth prog p) :loopout)
	p
	(recur (inc p))))
    (inc cp))
   mp mem])

(defn- loop-out [prog cp mp mem]
  [(if (zero? (nth mem mp))
     (inc cp)
     (loop [p cp]
       (if (= (nth prog p) :loopin)
	 p
	 (recur (dec p)))))
     mp mem])

; +[+[++]++]+
(defn parse-loops [prog]
  (map-indexed
    (fn [idx op]
      (if (= :loopin op)
        "looping"
        "other"))
    prog))

(defn find-matching-forward [prog cp]
  (loop [p cp level 0]
    (if (= (nth prog) :loop

(defn parse
  "Parse a brainfuck string into a vector of keywords (see operators)"
  [prog]
  (vec (filter (complement nil?)
	     (map #(operators %) prog))))

(defn run
  "Run a correct brainfuck program, parsed with braincloj.core/parse"
  [prog]
  (loop [cp 0 mp 0 mem (init-memory 10)]
    (if (< cp (count prog))
      (let [[cp mp mem] (case (nth prog cp)
			    :inc  [(inc cp) mp (assoc mem mp (inc (nth mem mp)))]
			    :dec  [(inc cp) mp (assoc mem mp (dec (nth mem mp)))]
			    :next [(inc cp) (inc mp) mem]
			    :prev [(inc cp) (dec mp) mem]
			    :loopin  (loop-in prog cp mp mem)
			    :loopout (loop-out prog cp mp mem)
			    :putc (do
				    (print (char (nth mem mp)))
				    [(inc cp) mp mem])
			    [(inc cp) mp mem])]
	;(println mem)
	(recur cp mp mem)))))

;hello world
;"++++++++++[>+++++++>++++++++++>+++>+<<<<-]>++.>+.+++++++..+++.>++.<<+++++++++++++++.>.+++.------.--------.>+.>."
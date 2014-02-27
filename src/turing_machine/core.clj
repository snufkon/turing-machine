(ns turing-machine.core)

(defn make-machine [rules tape]
  {:running true
   :current-state :state1
   :head-pos 0
   :rules rules
   :tape tape})

(defn read-tape [{:keys [tape head-pos]}]
  (get tape head-pos))

(defn write-tape [{:keys [head-pos] :as machine} val]
  (assoc-in machine [:tape head-pos] val))

(defn move-head [{:keys [head-pos] :as machine} direction]
  (case direction
    :left (if (= head-pos 0)
            (update-in machine [:tape] #(vec (cons %2 %1)) nil)
            (update-in machine [:head-pos] dec))
    :right (update-in machine [:head-pos] inc)
    :none machine))

(defn change-state [machine state]
  (if (= :state-fin state)
    (-> machine
        (assoc-in [:current-state] state)
        (assoc-in [:running] false))
    (assoc-in machine [:current-state] state)))

(defn step [machine]
  (let [state (:current-state machine)
        input (read-tape machine)
        rules (:rules machine)
        [val direction state] (get-in rules [state input])]
    (-> (write-tape machine val)
        (move-head direction)
        (change-state state))))

(defn run [{:keys [current-state head-pos tape] :as machine}]
  (println (str current-state "," head-pos ": " tape))
  (when (:running machine)
    (recur (step machine))))

;; ex1: lein run add.clj "[1 1 1 nil 1 1]" <-- calculate 3 + 2
;; ex2: lein run mul.clj "[1 1 1 1 :x 1 1]" <-- calculate 4 * 2
(defn -main [rule-file tape & args]
  (let [path (str "resources/rules/" rule-file)
        rules (:rules (read-string (slurp path)))
        tape (read-string tape)
        machine (make-machine rules tape)]
    (run machine)))

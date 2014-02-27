(ns turing-machine.core-test
  (:require [clojure.test :refer :all]
            [turing-machine.core :refer :all]))

(def add-rules (-> "resources/rules/add.clj"
                   slurp
                   read-string
                   :rules))

(deftest read-tape-test
  (testing "read head position value"
    (are [y x] (= y (read-tape {:tape [1 1 nil nil 1] :head-pos x}))
         1 0
         1 1
         nil 2
         nil 3
         1 4)))

(deftest write-tape-test
  (testing "write value to head position"
    (is (= [1 1 1 nil 1]
           (:tape (write-tape {:tape [1 1 nil nil 1] :head-pos 2} 1))))))

(deftest move-head-test
  (testing "current positin is not 0"
    (testing "move to left"
      (is (= 0 (:head-pos (move-head {:head-pos 1} :left)))))
    (testing "move to right"
      (is (= 2 (:head-pos (move-head {:head-pos 1} :right)))))
    (testing "no move"
      (is (= 1 (:head-pos (move-head {:head-pos 1} :none))))))
  (testing "current position is 0"
    (testing "extend tape"
      (is (= 6
             (count (:tape (move-head {:head-pos 0 :tape [1 1 nil nil 1]} :left))))))
    (testing "keep position"
      (is (= 0
             (:head-pos (move-head {:head-pos 0 :tape [1 1 nil nil 1]} :left)))))))

(deftest change-state-test
  (testing "change state"
    (is (= :state2
           (:current-state (change-state {:current-state :state1} :state2)))))
  (testing "stop runnning"
    (is (= false
           (:running (change-state {:running true :current-state :state3} :state-fin))))))

(deftest step-test
  (let [machine (make-machine add-rules [1 1 1 nil 1 1])
        new-machine (step machine)]
    (testing "write tape"
      (is (= [1 1 1 nil 1 1] (:tape new-machine))))
    (testing "move head position"
      (is (= 1 (:head-pos new-machine))))
    (testing "change state"
      (is (= :state1 (:current-state new-machine))))))

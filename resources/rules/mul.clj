;; When you want to calculate 4 * 2 using this rules,
;; you have to specify [1 1 1 1 :x 1 1] as a input tape.
{:rules {:state1 {1 [1 :left :state2]}
         :state2 {nil [:* :right :state3]}
         :state3 {nil [nil :left :state4]
                  :* [:* :right :state3]
                  1 [1 :right :state3]
                  :x [:x :right :state3]
                  :A [:A :right :state3]}
         :state4 {1 [nil :left :state5]
                  :x [:x :left :state10]}
         :state5 {1 [1 :left :state5]
                  :x [:x :left :state6]}
         :state6 {:* [:* :right :state9]
                  1 [:A :left :state7]
                  :A [:A :left :state6]}
         :state7 {nil [1 :right :state8]
                  :* [:* :left :state7]
                  1 [1 :left :state7]}
         :state8 {:* [:* :right :state8]
                  1 [1 :right :state8]
                  :x [:x :left :state6]
                  :A [:A :right :state8]}
         :state9 {nil [nil :left :state4]
                  1 [1 :right :state9]
                  :x [:x :right :state9]
                  :A [1 :right :state9]}
         :state10 {:* [nil :right :state11]
                   1 [1 :left :state10]}
         :state11 {nil [nil :none :state-fin]
                   1 [nil :right :state11]
                   :x [nil :right :state11]
                   :A [nil :right :state11]}}}

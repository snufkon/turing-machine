;; When you want to calculate 3 + 2 using this rules,
;; you have to specify [1 1 1 nil 1 1] as a input tape.
{:rules {:state1 {1 [1 :right :state1]
                  nil [1 :right :state2]}
         :state2 {1 [1 :right :state2]
                  nil [nil :left :state3]}
         :state3 {1 [nil :none :state-fin]}}}

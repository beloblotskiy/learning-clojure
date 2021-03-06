; pound, open quote, close quote: #"regular-expression"
; re-find - search by regex
; re-matches - entire string match a pattern
; re-seq - pull values out of string
; clojure.string/split - tokenizer


(def s "My phone number is 425-283-0000, please call during business hours")

(prn s)

(prn (re-find #"\d{3}-\d{3}-\d{4}" s))		; returns "425-283-0000"

(prn (clojure.string/replace s (re-find #"\d{3}-\d{3}-\d{4}" s) "XXX-XX-XXXX"))		; mask my phone number




(def s "Home phone: 425-283-0000, business phone: 425-283-0001")

(prn (re-find #"\d{3}-\d{3}-\d{4}" s))		; returns "425-283-0000"

(prn (clojure.string/replace s (re-find #"\d{3}-\d{3}-\d{4}" s) "XXX-XX-XXXX"))		; mask first phone only



; Tokenize string with regex
(prn (re-seq #"\w*" "I love Clojure and Hadoop"))			; ("I" "" "love" "" "Clojure" "" "and" "" "Hadoop" "")
(prn (re-seq #"\b\w+\b" "I love Clojure and Hadoop"))		; ("I" "love" "Clojure" "and" "Hadoop")

(prn (clojure.string/split "And Storm too" #" "))				; ["And" "Storm" "too"]


; Define map
; Syntax 1
(def subaru-models
     {:SUV "Forester"
      :wagon "Outback"
      :sedan "Legacy"
      :hatchback "Impreza"})

(prn subaru-models)

; getting values
(prn (get subaru-models :wagon))			; "Outback"
(prn (get subaru-models :cabriolet))		; nil
(prn (get subaru-models :cabriolet "N/A"))	; "N/A"

; using function juxtaposition for selecting multiple values
(prn ((juxt :wagon) subaru-models))		; ["Outback"]   -- in-fact (:wagon subaru-models)
(prn (:wagon subaru-models))			; "Outback"
(prn ((juxt :wagon :SUV) subaru-models))	; ["Outback" "Forester"]


; Syntax 2
(def subaru-models
     {"SUV" "Forester",
      "wagon" "Outback",
      "sedan" "Legacy",
      "hatchback" "Impreza"})

(prn subaru-models)

; getting values (functional syntax)
(prn (subaru-models "wagon"))			; "Outback"
(prn (subaru-models "cabriolet"))			; nil
(prn (subaru-models "cabriolet" "N/A"))		; "N/A"

; ---------------------------------------------------------------------------------------------------------------------

(def math-ops
    {:plus +
     :minus -
     :mul *
     :div /})

(prn math-ops)

(prn ((get math-ops :plus) 100 200))
(prn ((get math-ops :div) 100 300))
(prn (get math-ops :xyz))				; key xyz is absent in map, nil will be returned
; (prn ((get math-ops :xyz) 100 300))			; NullPointerException
(prn ((get math-ops :xyz str) 100 300))		; use default value if key is missing

; ---------------------------------------------------------------------------------------------------------------------

; get-in -- traverse thru nested maps (generally into any nested structures)

(def nested-map {"k1" {"k11" "v11" "k12" "v12"} "k2" {"k21" "v21" "k22" {"k221" "v221"}}})

(prn (get-in nested-map ["k1" "k12"]))		; "v12"
(prn (get-in nested-map ["k1" "k13"]))		; nil
(prn (get-in nested-map ["k1" "k13"] "N/A"))	; "N/A"
(prn (get-in nested-map ["k2" "k22"]))		; {"k221" "v221"}
(prn (get-in nested-map ["k2" "k22" "k221"]))	; "v221"

(def vec-of-maps [{"k1" {"k11" "v11" "k12" "v12"}} {"m1" {"m11" "w11" "m12" "w12"}}])

(prn (get-in vec-of-maps [0 "k1" "k11"]))		; "v11"
(prn (get-in vec-of-maps [1 "m1" "m11"]))	; "w11"
(prn (get-in vec-of-maps [2 "m1" "m11"]))	; nil

; ---------------------------------------------------------------------------------------------------------------------

; selecting multiple keys (create map which is "subset" of another map)

(prn (select-keys subaru-models ["wagon" "SUV"]))			; {"SUV" "Forester", "wagon" "Outback"}

; ---------------------------------------------------------------------------------------------------------------------

; changing keys in a map

(def m1 {1 10 2 20 3 30})
(prn (assoc m1 1 11))					; {1 11, 3 30, 2 20}		-- change existing key
(prn (assoc m1 4 40))					; {1 10, 4 40, 3 30, 2 20}	-- add new key
(prn (dissoc m1 1 2))					; {3 30}			-- remove keys

; ---------------------------------------------------------------------------------------------------------------------

; using composite keys -- ok because of immutable Clojure objects

(def mc {{:k1 :v1} 100 [:k2] 200 '(:k3) 300 #(1 2 3) 400 + 500})
(prn (get mc {:k1 :v1}))				; 100
(prn (mc [:k2]))						; 200
(prn (get mc '(:k3)))					; 300
(prn (get mc #(1 2 3)))				; nil
(prn (dissoc mc #(3 2 1)))				; isn't removed: {{:k1 :v1} 100, [:k2] 200, (:k3) 300, #<user$fn__57 user$fn__57@6424f87e> 400, #<core$_PLUS_ clojure.core$_PLUS_@5adf20ae> 500}
(prn (dissoc mc #(1 2 3)))				; isn't removed: {{:k1 :v1} 100, [:k2] 200, (:k3) 300, #<user$fn__57 user$fn__57@1b341f7c> 400, #<core$_PLUS_ clojure.core$_PLUS_@5fd83099> 500}

; ---------------------------------------------------------------------------------------------------------------------

; treat map as sequence

; map to seq
(def m2 {1 10 2 20 3 30})
(prn (seq m2))						; ([1 10] [3 30] [2 20])

; seq to map
(prn (conj m2 [4 40]))					; {1 10, 4 40, 3 30, 2 20}
(prn (into m2 [[4 40] [5 50]]))			; {1 10, 4 40, 3 30, 2 20, 5 50}
(prn (into {} [[4 40] [5 50]]))			; {4 40, 5 50}

; zip -- create map from two sequences: keys and values
(prn (zipmap [1 2 3 4 5] [10 20 30 40 50]))	; {5 50, 4 40, 3 30, 2 20, 1 10}

; ---------------------------------------------------------------------------------------------------------------------

; processing maps
(prn (keys m2))						; (1 3 2)  -- access to keys
(prn (vals m2))						; (10 30 20)  -- access to values

; process map keys
(prn (zipmap (map inc (keys m2)) (vals m2)))			; {3 20, 4 30, 2 10}

; process keys and values
(prn (into {} (map (fn [[k v]] [(inc k) (dec v)]) m2)))	; {2 9, 4 29, 3 19}

; ---------------------------------------------------------------------------------------------------------------------

; merging maps

(def m1 {"a" 1 "b" 2 "c" 3})
(def m2 {"a" 3 "b" 5 "d" 7})
(prn (merge-with + m1 m2))						; {"d" 7, "a" 4, "b" 7, "c" 3}

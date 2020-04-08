(ns ^:no-doc zprint.cutil
  (:require [zprint.config :refer [get-options]]))

(defn merge-colors [a b]
  (cond
    (and (sequential? a) (sequential? b)) (into (vec a) b)
    (sequential? a) (into (vec a) (vector b))
    (sequential? b) (into (vector a) b)
    :else [a b]))

(defn cmap [color-map]
  {:color-map
   (merge-with merge-colors (:color-map (get-options)) color-map)})

(defn kcol [key-color]
  (let [{kw :keyword, :keys [number string]} (:color-map (get-options))]
    {:key-color
     (into {} (map (fn [[k v]]
                     (cond
                       (keyword? k) [k (merge-colors kw v)]
                       (string? k) [k (merge-colors string v)]
                       (number? k) [k (merge-colors number v)]
                       :else [k v]))
                   key-color))}))

(defn cstyle [ckeys style]
  (cmap (into {} (for [ckey ckeys] [ckey style]))))

(defn kstyle [mkeys style]
  (kcol (into {} (for [mkey mkeys] [mkey style]))))

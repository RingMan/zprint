(ns ^:no-doc zprint.cutil
  "Provides utility functions for creating maps suitable for :color-map,
  :key-color, and :key-value-color zprint options. These are especially
  useful at the REPL or for preserving an existing color theme while
  adding styling attributes like :bold, :underline, or :reverse."
  (:require [zprint.config :refer [get-options]]))

(defn merge-colors [a b]
  (cond
    (and (sequential? a) (sequential? b)) (into (vec a) b)
    (sequential? a) (into (vec a) (vector b))
    (sequential? b) (into (vector a) b)
    :else [a b]))

(defn cmap
  "Adds the styling for each key in `color-map` to the underlying style in
  `base-map` or the default color map and returns the resulting map."
  ([color-map] (cmap color-map nil))
  ([color-map base-map]
   (merge-with
     merge-colors
     (select-keys (or base-map (:color-map (get-options))) (keys color-map))
     color-map)))

(defn cmap*
  "Wraps result of `cmap` in a one element map whose key is :color-map"
  ([color-map] (cmap* color-map nil))
  ([color-map base-map]
   {:color-map (cmap color-map base-map)}))

(defn kcol
  "Adds the styling for each key in `key-color` to the underlying style for
  that key's type in `base-map` or the default color map and returns the
  resulting map."
  ([key-color] (kcol key-color nil))
  ([key-color base-map]
   (let [{kw :keyword, :keys [number string]}
         (or base-map (:color-map (get-options)))]
     (into {} (map (fn [[k v]]
                     (cond
                       (keyword? k) [k (merge-colors kw v)]
                       (string? k) [k (merge-colors string v)]
                       (number? k) [k (merge-colors number v)]
                       :else [k v]))
                   key-color)))))

(defn kcol*
  "Wraps result of `kcol` in a one element map whose key is :key-color"
  ([key-color] (kcol* key-color nil))
  ([key-color base-map]
   {:key-color (kcol key-color base-map)}))

(defn ks->v
  "Map from each key in `ks` to the value `v`"
  [ks v] (into {} (for [k ks] [k v])))

(defn cstyle
  "Uses `cmap` to add the same styling to multiple keys in a color-map"
  ([ckeys style] (cstyle ckeys style nil))
  ([ckeys style base-map]
   (cmap (ks->v ckeys style) base-map)))

(defn cstyle*
  "Wraps result of `cstyle` in a one element map whose key is :color-map"
  ([ckeys style] (cstyle* ckeys style nil))
  ([ckeys style base-map]
   {:color-map (cstyle ckeys style base-map)}))

(defn kstyle
  "Uses `kcol` to add the same styling to multiple keys in a key-color map"
  ([mkeys style] (kstyle mkeys style nil))
  ([mkeys style base-map]
   (kcol (ks->v mkeys style) base-map)))

(defn kstyle*
  "Wraps result of `kstyle` in a one element map whose key is :key-color"
  ([mkeys style] (kstyle* mkeys style nil))
  ([mkeys style base-map]
   {:key-color (kstyle mkeys style base-map)}))


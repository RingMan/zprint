(ns ^:no-doc zprint.rutil
  "Provides REPL utility functions for making `czprint` the `*print-fn*`."
  (:require [zprint.core :refer [czprint]]))

; next two fns lifted from whidbey and adapted to czprint

(defn render
  "Renders the given value for display by pretty-printing it on the given writer
  using czprint."
  [value writer]
  (binding [*out* writer] (czprint value)))

(defn update-print-fn!
  "Updates nREPL's printing configuration to use `render-fn` nREPL 0.6.0+ only."
  [render-fn]
  (some-> (find-ns 'nrepl.middleware.print)
          (ns-resolve '*print-fn*)
          (var-set render-fn)))


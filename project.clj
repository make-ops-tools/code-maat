(defproject code-maat "0.1.0-SNAPSHOT"
  :description "A toolset to mine and analyze version control data"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [org.clojure/data.zip "0.1.0"]
		 [incanter "1.4.1"]
                 [org.clojure/tools.cli "0.2.4"]
                 [org.clojure/data.csv "0.1.2"]]
  :main code-maat.core)

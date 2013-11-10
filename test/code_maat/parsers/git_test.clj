;;; Copyright (C) 2013 Adam Tornhill
;;;
;;; Distributed under the GNU General Public License v3.0,
;;; see http://www.gnu.org/licenses/gpl.html

(ns code-maat.parsers.git-test
  (:require [code-maat.parsers.git :as git]
            [incanter.core :as incanter])
  (:use clojure.test incanter.core))

;;; I sure could have used a more minimalistic set of data.
;;; Instead I went with some live data from my projects.
;;; Better ecological validity - we're still just testing
;;; one module though.

(def ^:const entry
  "[990442e] Adam Petersen 2013-08-29 Adapted the grammar after live tests (git)
1	0	project.clj
2	4	src/code_maat/parsers/git.clj
")

(def ^:const binary-entry
  "[990442e] Adam Petersen 2013-11-10 Testing binary entries
-	-	project.bin
2	40	src/code_maat/parsers/git.clj
")

(def ^:const entries
  "[b777738] Adam Petersen 2013-08-29 git: parse merges and reverts too (grammar change)
10	9	src/code_maat/parsers/git.clj
32	0	test/code_maat/parsers/git_test.clj

[a527b79] Adam Petersen 2013-08-29 git: proper error messages from instaparse
6	2	src/code_maat/parsers/git.clj
0	7	test/code_maat/end_to_end/scenario_tests.clj
18	0	test/code_maat/end_to_end/simple_git.txt
21	0	test/code_maat/end_to_end/svn_live_data_test.clj
")

(deftest throws-on-invalid-input
  (is (thrown? IllegalArgumentException
               (git/parse-log "simply not a valid git log here..." {}))))

(deftest parses-single-entry-to-dataset
  (is (= (incanter/to-list (git/parse-log entry {}))
         [["0" "1" "Adam Petersen" "990442e" "2013-08-29" "project.clj"]
          ["4" "2" "Adam Petersen" "990442e" "2013-08-29" "src/code_maat/parsers/git.clj"]])))

(deftest parses-entry-with-binary-to-dataset
  "The churn for binary entries are given as a dash (-)."
  (is (= (incanter/to-list (git/parse-log binary-entry {}))
         [["-"  "-" "Adam Petersen" "990442e" "2013-11-10" "project.bin"]
          ["40" "2" "Adam Petersen" "990442e" "2013-11-10" "src/code_maat/parsers/git.clj"]])))

(deftest parses-multiple-entries-to-dataset
  (is (= (incanter/to-list (git/parse-log entries {}))
         [["9"  "10" "Adam Petersen" "b777738" "2013-08-29" "src/code_maat/parsers/git.clj"]
          ["0"  "32" "Adam Petersen" "b777738" "2013-08-29" "test/code_maat/parsers/git_test.clj"]
          ["2"   "6" "Adam Petersen" "a527b79" "2013-08-29" "src/code_maat/parsers/git.clj"]
          ["7"   "0" "Adam Petersen" "a527b79" "2013-08-29" "test/code_maat/end_to_end/scenario_tests.clj"]
          ["0"  "18" "Adam Petersen" "a527b79" "2013-08-29" "test/code_maat/end_to_end/simple_git.txt"]
          ["0"  "21" "Adam Petersen" "a527b79" "2013-08-29" "test/code_maat/end_to_end/svn_live_data_test.clj"]])))

(deftest parses-empty-log-to-empty-dataset
  (is (= (incanter/to-list (git/parse-log "" {}))
         [])))
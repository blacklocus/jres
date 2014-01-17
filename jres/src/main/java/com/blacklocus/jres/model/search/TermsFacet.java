package com.blacklocus.jres.model.search;

import java.util.List;

public class TermsFacet extends Facet {

    private List<Term> terms;

    public List<Term> getTerms() {
        return terms;
    }

    public static class Term {

        private String term;
        private Long count;

        public Term() {
        }

        public Term(String term, Long count) {
            this.term = term;
            this.count = count;
        }

        public String getTerm() {
            return term;
        }

        public Long getCount() {
            return count;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            Term term1 = (Term) o;

            if (count != null ? !count.equals(term1.count) : term1.count != null) return false;
            if (term != null ? !term.equals(term1.term) : term1.term != null) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = term != null ? term.hashCode() : 0;
            result = 31 * result + (count != null ? count.hashCode() : 0);
            return result;
        }
    }
}

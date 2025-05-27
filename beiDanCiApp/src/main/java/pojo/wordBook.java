package pojo;


    public  class wordBook {
        private int id;
        private String name;
        private String description;

        public wordBook(String name) {
            this.name = name;
        }


        public wordBook() {

        }

        public String     getName() { return name; }
        public String getDescription() { return description; }


        public void setName(String name) {
            this.name = name;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        @Override
        public String toString() {
            return "wordBook{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }

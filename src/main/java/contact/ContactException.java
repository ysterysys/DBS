package contact;

class ContactException extends RuntimeException {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    ContactException(Long id) {
    super("Could not find contact " + id);
  }
}
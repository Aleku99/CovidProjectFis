public abstract class Encryption {
    private static final int abcdefg = 9404;

    public static String encrypt_password(String pass)
    {
        StringBuilder encrypted_string = new StringBuilder();

        int rand_seed = abcdefg / pass.length();

        int position = 0;
        for(Character c:pass.toCharArray())
        {
            if(position == 0) {
                position += 1;
                encrypted_string.append((char) ((int) c + 1));
            }
            else {
                encrypted_string.append((char) ((int) c + 2));
                position = 0;
            }
        }
        return encrypted_string.toString();

    }

    public static String decrypt_password(String pass)
    {
        StringBuilder decrypted_string = new StringBuilder();

        int rand_seed = abcdefg / pass.length();

        int position = 0;
        for(Character c:pass.toCharArray())
        {
            if(position == 0) {
                position += 1;
                decrypted_string.append((char) ((int) c - 1));
            }
            else {
                decrypted_string.append((char) ((int) c - 2));
                position = 0;
            }
        }
        return decrypted_string.toString();
    }
}
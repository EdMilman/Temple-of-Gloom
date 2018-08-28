package main;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TXTmainTest {
    @Test
    public void run100Times() throws IOException {
        String[] args = {"-n", "100"};
        TXTmain.main(args);
    }

}
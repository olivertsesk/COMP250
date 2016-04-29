package a3;

import org.junit.Test;

import static org.junit.Assert.*;

public class TokenizerTest {

    @Test
    public void testTokenize() throws Exception {
        Tokenizer tokenizer = new Tokenizer();
        String string = " (2+(1 23+123 )/23)";
        tokenizer.tokenize(string);
        System.out.println(tokenizer.getTokenList().toString());
        for (Token token:tokenizer.getTokenList()){
            System.out.println(string.substring(token.getStart(), token.getEnd())+" type:" + token.getType().toString());
        }
        tokenizer.parse(string);
        System.out.println(tokenizer.getTokenList().toString());
        for (Token token:tokenizer.getTokenList()){
            System.out.println(string.substring(token.getStart(), token.getEnd()));
        }

    }
}
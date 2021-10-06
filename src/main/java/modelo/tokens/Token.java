package modelo.tokens;

import lombok.*;
import modelo.tablas.Arrayable;

@Builder
@Getter
@Setter
public class Token implements Arrayable {
    String tocken;
    String tipoToken;


    @Override
    public String[] toArray() {
        return new String[] {tocken, tipoToken};
    }
}

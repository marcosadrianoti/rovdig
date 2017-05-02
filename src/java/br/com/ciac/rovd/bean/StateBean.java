
package br.com.ciac.rovd.bean;

import br.com.ciac.rovd.entidade.State;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class StateBean {
    private State state = new State();

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
    
    
}

package br.com.skill.model;

public class RespostaUsuario {
    
    private Integer idRespostaUsuario;
    private Integer idProvaUsuario;
    private Integer idQuestao;
    private String resposta;
    private String correta;
    
    public RespostaUsuario() {
    }
    
    public RespostaUsuario(Integer idRespostaUsuario, Integer idProvaUsuario, 
                           Integer idQuestao, String resposta, String correta) {
        this.idRespostaUsuario = idRespostaUsuario;
        this.idProvaUsuario = idProvaUsuario;
        this.idQuestao = idQuestao;
        this.resposta = resposta;
        this.correta = correta;
    }
    
    public Integer getIdRespostaUsuario() {
        return idRespostaUsuario;
    }
    
    public void setIdRespostaUsuario(Integer idRespostaUsuario) {
        this.idRespostaUsuario = idRespostaUsuario;
    }
    
    public Integer getIdProvaUsuario() {
        return idProvaUsuario;
    }
    
    public void setIdProvaUsuario(Integer idProvaUsuario) {
        this.idProvaUsuario = idProvaUsuario;
    }
    
    public Integer getIdQuestao() {
        return idQuestao;
    }
    
    public void setIdQuestao(Integer idQuestao) {
        this.idQuestao = idQuestao;
    }
    
    public String getResposta() {
        return resposta;
    }
    
    public void setResposta(String resposta) {
        this.resposta = resposta;
    }
    
    public String getCorreta() {
        return correta;
    }
    
    public void setCorreta(String correta) {
        this.correta = correta;
    }
}


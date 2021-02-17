package br.com.bigsupermercados.audit.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "foto")
public class Foto {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long codigo;
	
	@Column(name = "caminho")
	private String caminho;
	
	@Column(name = "data_hora_inclusao")
	private LocalDateTime dataHoraInclusao = LocalDateTime.now();
	
	@ManyToOne
	@JoinColumn(name = "resposta_codigo")
	private Resposta resposta;
	
	public Foto() {
		
	}
	
	public Foto(String nome) {
		this.caminho = nome;
	}
}

package br.com.bigsupermercados.audit.security;

import java.io.File;

import br.com.bigsupermercados.audit.storage.local.FotoStorageLocal;

public class GerarThumbnail {

	public static void main(String[] args) {
		FotoStorageLocal fotoStorageLocal = new FotoStorageLocal();

		File file = new File("C:\\Users\\Ismael Junior\\Desktop\\null\\.brewerfotos\\temp");
		File afile[] = file.listFiles();

		int i = 0;

		for (int j = afile.length; i < j; i++) {
			File arquivo = afile[i];
			System.out.println(arquivo.getAbsolutePath());
			fotoStorageLocal.gerarThumbnail(arquivo.getAbsolutePath());
		}
	}
}

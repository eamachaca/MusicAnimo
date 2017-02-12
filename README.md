# MusicAnimo
Proyecto que reproduce una lista de reproduccion en base a algun estado de animo (dicho estado de animo se lo captura 
por medio de Deteccion facial)

Hecho con FaceTracker de Google Vision (agregada como libreria, con algunas modificaciones). 
Reconocimiento de Rostro(Ojo esta libreria no es un para nada un comparador, solo detecta si existe un rostro (digase Cara)
y viene con algunas propiedades (getIsSmilingProbability, etc) la mencionada retorna algo asi como el grado de felicidad 
EXPRESIVA de la persona. 

EL PROYECTO ESTA DISEÑADO PARA UNA PERSONA, NO ASI PARA UN GRUPO (en ese caso es el primero que capture (puede ser cualquiera)
ya que es un scan, no se necesita sacar foto)

Reproductor Stream hecho con libreria nativa de Android (MediaPlayer) musicas capturadas por medio de un WebService's
dicho Servicio Web esta desarrollado con la libreria Volley (y el patron Singleton).

Espero puedan disfrutar dicho

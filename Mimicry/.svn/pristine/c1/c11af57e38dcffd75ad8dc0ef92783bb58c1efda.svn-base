package com.c.local.mimicry;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.os.Environment;

public class MimicryEffecter {
	static final int AUDIO_SAMPLE_FREQ = 8000;//8000.22050.44100	
	static final int AUDIO_BUFFER_SIZE = Math.max(AUDIO_SAMPLE_FREQ * 2 * 10,
			AudioRecord.getMinBufferSize(AUDIO_SAMPLE_FREQ,
					AudioFormat.CHANNEL_CONFIGURATION_MONO,
					AudioFormat.ENCODING_PCM_16BIT));

	private final File path = new File(
			Environment
					.getExternalStoragePublicDirectory(Environment.DIRECTORY_MUSIC),
			"TMP");
	private File file = new File(path, "original.wav");
	private File w_file = new File(path, "effect.wav");
	
	private static final String SDCRD_PATH = Environment
			.getExternalStorageDirectory() + "/MIMICRY";
	private static final String REC_PATH = "rec";
	private static final String LIB_PATH = "lib";
	
	
	private byte[] rawData;
	
	public byte[] getRawData() {
		return rawData;
	}

	public void setRawData(byte[] rawData) {
		this.rawData = rawData;
	}

	private byte[] effectedData;
	private Parameters parameters = new Parameters();

	private MimicryRecorder recorder = new MimicryRecorder(AUDIO_SAMPLE_FREQ);
	private MimicryPlayer player = new MimicryPlayer(AUDIO_SAMPLE_FREQ);

	public MimicryEffecter() {
		if (!path.exists()) {
			path.mkdirs();
		}
	}

	public void startRecording() {
		recorder.start();
	}

	public void stopRecording() {
		recorder.stop();

		try {
			// 再生するデータの取得
			file.createNewFile();
			rawData = recorder.getData(); // 元音声
			effectedData = rawData.clone(); // エフェクトをかける音声

			FileOutputStream out = new FileOutputStream(file);
			out.write(raw2wav(rawData)); // wav形式に変換して保存
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startPlaying() {
		player.setWav(effectedData); // 音声のセット
		player.start();
	}
	
	public void startSaveDataPlaying(String file) throws IOException {
		player.setWavFile(file); // 音声のセット
		player.start();
	}
	
	
	public void setWebSaveData(String fileUrl) throws IOException {
		URL wavUrl = new URL(fileUrl);
		InputStream wavis = wavUrl.openStream();
		rawData = getBytes(wavis);
	}
	
	public void startWebSaveDataPlaying(){
		
//		URL wavUrl = new URL(fileUrl);
//		InputStream wavis = wavUrl.openStream();
//		rawData = getBytes(wavis);
////		Bitmap bm = BitmapFactory.decodeStream(wavis);
//		((ImageView)view).setImageBitmap(bm);
//		file.createNewFile();
//		FileOutputStream out = new FileOutputStream(file);
//		out.write(raw2wav(rawData)); // wav形式に変換して保存
//		out.close();
		
		player.setWav(rawData); // 音声のセット
		player.start();
	}

	private byte[] getBytes(InputStream is) {
		ByteArrayOutputStream b = new ByteArrayOutputStream();
		OutputStream os = new BufferedOutputStream(b);
		int c;
		try {
			while ((c = is.read()) != -1) {
				os.write(c);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.flush();
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
		// 書き込み先はByteArrayOutputStreamクラス内部となる。
		// この書き込まれたバイトデータをbyte型配列として取り出す場合には、
		// toByteArray()メソッドを呼び出す。 
		return b.toByteArray();
		
	}

	public void stopPlaying() {
		player.stop();
	}

	
	
	public void saveFile(String fineName) {
		
		File savefile = new File(SDCRD_PATH + "/" + LIB_PATH + "/" + fineName);
		try {
			// 再生するデータの取得
			savefile.createNewFile();
			rawData = recorder.getData(); // 元音声
			effectedData = rawData.clone(); // エフェクトをかける音声

			FileOutputStream out = new FileOutputStream(savefile);
			out.write(raw2wav(rawData)); // wav形式に変換して保存
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	/**
	 * raw音声をwav形式に変換
	 * 
	 * @param rawData
	 * @return
	 * @throws IOException
	 */
	public byte[] raw2wav(byte[] rawData) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		out.write("RIFF".getBytes());
		out.write(i2littleByte(rawData.length + 36, 4));
		out.write("WAVE".getBytes());
		out.write("fmt ".getBytes());
		out.write(i2littleByte(16, 4));
		out.write(i2littleByte(1, 2));
		out.write(i2littleByte(1, 2));
		out.write(i2littleByte(AUDIO_SAMPLE_FREQ, 4));
		out.write(i2littleByte(AUDIO_SAMPLE_FREQ * 2, 4));
		out.write(i2littleByte(2, 2));
		out.write(i2littleByte(16, 2));
		out.write("data".getBytes());
		out.write(i2littleByte(rawData.length, 4));
		out.write(rawData);
		return out.toByteArray();
	}

	/**
	 * パラメータの設定
	 * 
	 * @param parameters
	 */
	public void setParameters(Parameters parameters) {
		this.parameters = parameters;
	}

	/**
	 * パラメータの取得
	 * 
	 * @return
	 */
	public Parameters getParameters() {
		return parameters;
	}

	/**
	 * intをリトルエンディアンの配列に変換
	 * 
	 * @param value
	 *            int value
	 * @param size
	 *            array size
	 * @return
	 */
	public static byte[] i2littleByte(int value, int size) {
		byte[] b = new byte[size];
		for (int i = 0; i < size; i++, value >>= 8) {
			b[i] = (byte) (value & 0xff);
		}
		return b;
	}

	/**
	 * raw音声をparameterにしたがって変換
	 */
	public void convert() {
		try {
			// 各エフェクトの適用
			effectedData = changePitch(rawData, parameters.getPitch());
			effectedData = changeVolume(effectedData, parameters.getVolume());
			effectedData = changeSpeed(effectedData, parameters.getSpeed());
			if (parameters.getReverseFlag()) {
				effectedData = reverse(effectedData);
			}
			if (parameters.getEchoDelay() > 0) {
				effectedData = addEchoEffect(effectedData,
						parameters.getEchoDelay(), parameters.getEchoVolume());
			}
			// ファイル書き込み
			w_file.createNewFile();
			FileOutputStream out = new FileOutputStream(w_file);
			out.write(raw2wav(effectedData));
			out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 変換パラメータのreset
	 */
	public void resetParameters() {
		parameters = new Parameters();
	}

	/**
	 * プレイヤーの再生位置取得
	 * 
	 * @return
	 */
	public float getCurrentProgress() {
		if (player.isPlaying()) {
			return player.getCurrentProgress();
		}
		return 0.0f;
	}

	/**
	 * スピードを変換
	 * 
	 * @param data
	 * @param speed
	 * @return
	 */
	public static byte[] changeSpeed(byte[] data, float speed) {
		if (data == null) {
			return new byte[] {};
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		for (int i = 0; i < data.length / 2 / speed; i++) {
			out.write(data[(int) (i * speed) * 2]);
			out.write(data[(int) (i * speed) * 2 + 1]);
		}

		return out.toByteArray();
	}

	/**
	 * ピッチを変換
	 * 
	 * @param data
	 * @param pitch
	 * @return
	 */
	public static byte[] changePitch(byte[] data, float pitch) {
		if (data == null) {
			return new byte[] {};
		}

		int index;
		int n = 200;
		int offset = 0;
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		while (offset + n * 2 < data.length) {
			for (int i = 0; i < n; i++) {
				index = ((int) Math.floor(i * pitch) % n) * 2;
				out.write(data[offset + index]);
				out.write(data[offset + index + 1]);
			}
			offset += n * 2;
		}
		return out.toByteArray();
	}

	/**
	 * ボリュームを変換
	 * 
	 * @param data
	 * @param volume
	 * @return
	 */
	public static byte[] changeVolume(byte[] data, float volume) {
		if (data == null) {
			return new byte[] {};
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int val;
		for (int i = 0; i < data.length / 2; i++) {
			val = ((int) data[2 * i + 1] << 8) + data[2 * i];
			byte[] b1 = i2littleByte((int) (val * volume), 2);
			out.write(b1[0]);
			out.write(b1[1]);
		}
		return out.toByteArray();
	}

	/**
	 * 逆再生
	 * 
	 * @param data
	 * @return
	 */
	public static byte[] reverse(byte[] data) {
		if (data == null) {
			return new byte[] {};
		}

		ByteArrayOutputStream out = new ByteArrayOutputStream();
		for (int i = data.length / 2 - 1; i >= 0; i--) {
			out.write(data[i * 2]);
			out.write(data[i * 2 + 1]);
		}
		return out.toByteArray();
	}

	/**
	 * エコーをかける
	 * 
	 * @param data
	 * @param delay
	 * @param volume
	 * @return
	 */
	public static byte[] addEchoEffect(byte[] data, int delay, float volume) {
		if (data == null) {
			return new byte[] {};
		}
		if (volume > 0.9f) {
			volume = 0.9f;
		} else if (volume < 0.0f) {
			volume = 0.0f;
		}

		byte[] echo = data.clone();
		for (int i = 0; i < data.length; i++) {
			if (i > delay) {
				echo[i] += echo[i - delay] * volume;
			}
		}
		return echo;
	}

	/**
	 * 変換のパラメータ
	 * 
	 */
	class Parameters {
		private float speed = 1.0f;
		private float pitch = 1.0f;
		private float volume = 1.0f;
		private int echoDelay = 0;
		private float echoVolume = 0.3f;
		private boolean reverseFlag = false;

		public void setSpeed(float speed) {
			this.speed = speed;
		}

		public float getSpeed() {
			return speed;
		}

		public void setPitch(float pitch) {
			this.pitch = pitch;
		}

		public float getPitch() {
			return pitch;
		}

		public void setVolume(float volume) {
			this.volume = volume;
		}

		public float getVolume() {
			return volume;
		}

		public void setEchoDelay(int echoDelay) {
			this.echoDelay = echoDelay;
		}

		public int getEchoDelay() {
			return echoDelay;
		}

		public void setEchoVolume(float echoVolume) {
			this.echoVolume = echoVolume;
		}

		public float getEchoVolume() {
			return echoVolume;
		}

		public void setReverseFlag(boolean reverseFlag) {
			this.reverseFlag = reverseFlag;
		}

		public boolean getReverseFlag() {
			return reverseFlag;
		}
	}
}

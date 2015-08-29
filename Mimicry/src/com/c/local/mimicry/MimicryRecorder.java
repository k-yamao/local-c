package com.c.local.mimicry;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

public class MimicryRecorder implements Runnable {
	private Thread thread;
	private boolean recordFlag;
	private long startRecordTime;
	private AudioRecord audioRecord;
	private int sampleRate;
	public static final long MAX_RECORD_TIME = 1000 * 15;
	private int bufferSize;
	private ByteArrayOutputStream byteStream;

	public MimicryRecorder(int sampleRate) {
		thread = null;
		recordFlag = false;
		this.sampleRate = sampleRate;
		bufferSize = AudioRecord.getMinBufferSize(sampleRate,
				AudioFormat.CHANNEL_CONFIGURATION_MONO,
				AudioFormat.ENCODING_PCM_16BIT) * 2;

	}

	/**
	 * 録音開始
	 */
	public void start() {
		if (thread == null) {
			thread = new Thread(this);
			recordFlag = true;
			thread.start();
		}
	}

	/**
	 * 録音停止
	 */
	public void stop() {
		recordFlag = false;
	}

	/**
	 * 録音データの取得
	 * 
	 * @return
	 */
	public byte[] getData() {
		if (byteStream != null) {
			return byteStream.toByteArray();
		}
		return new byte[] {};
	}

	public boolean isRecording() {
		return thread != null;
	}

	@Override
	public void run() {
		startRecording();
		startRecordTime = System.currentTimeMillis();
		byteStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[bufferSize];
		// 最長録音時間を超えたら自動停止 その間データを取得
		while (recordFlag
				&& System.currentTimeMillis() - startRecordTime < MAX_RECORD_TIME) {
			try {
				Thread.sleep(1);
				audioRecord.read(buffer, 0, buffer.length);
				byteStream.write(buffer);
			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		stopRecording();
	}

	/**
	 * AudioRecordの設定
	 */
	private void startRecording() {
		try {
			audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC,
					sampleRate, AudioFormat.CHANNEL_IN_MONO,
					AudioFormat.ENCODING_PCM_16BIT, bufferSize);
			// 録音開始
			audioRecord.startRecording();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * AudioRecordの停止
	 */
	private void stopRecording() {
		// AudioRecordの停止と解放
		audioRecord.stop();
		audioRecord.release();
		thread = null;
	}
}

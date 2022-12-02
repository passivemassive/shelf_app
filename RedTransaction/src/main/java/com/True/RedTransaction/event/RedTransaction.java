package com.True.RedTransaction.event;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RedTransaction {

	private int redMessageId;
	private String redMessage;
	private LocalDateTime timeCreated;

}

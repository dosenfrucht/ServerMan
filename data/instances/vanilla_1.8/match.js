var pat

function init() {
	pat = /\[([0-9\:]+)\] \[(.*)\]: (.*)/
}

function match(line) {
	var m = pat.exec(line)
	if (m !== null) {
		writer.receive("info", m[1], m[2], m.slice(3))
	}
}
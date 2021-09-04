/**
 * 
 */
const parser = new DOMParser();
const state = document.querySelector("#state");
const county_group = document.querySelector("#county_group");
const counties_state = await fetch("/js/counties.json").then(res => res.json());

function optionString(index, value) {
	return `<option value="${index + 1}">${value}</option>`
}

function insertCounties(counties) {
	counties.forEach((value, index) => {
		const doc = parser.parseFromString(optionString(index, value), "text/html");
		county_group.appendChild(doc.querySelector("option"));
	});
}

async function res(event) {
	county_group.textContent = "";
	insertCounties(counties_state[event.target.value]);
}

state.addEventListener("change", res);